#pragma once

#include "BaseFilter.h"
#include "Image.h"
#include "Pixel.h"
#include "SharpMatrix.h"

#include <vector>
#include <stdexcept>

template <typename Matrix>
	class MatrixFilter : public BaseFilter
	{
		Matrix * matrix;

		const float div;

		const std::string WRONG_FILTER_STR = "Unworking filter!";

		std::vector<std::vector<Pixel*>> pixels;

		void fillNullptrPixels()
		{
			for (size_t k = 0; k < pixels.size(); ++k)
			{
				for (size_t i = 0; i < pixels[k].size(); ++i)
				{
					pixels[k][i] = nullptr;
				}
			}
		}

		void deleteTempPixels()
		{
			for (size_t i = 0; i < pixels.size(); ++i)
			{
				for (size_t k = 0; k < pixels[i].size(); ++k)
				{
					delete pixels[i][k];
				}
			}

			pixels.clear();
		}	

		const Pixel & getNearPixel(const Image & image, long long x, long long y)
		{
			size_t pixelX, pixelY;

			if (x < 0)
			{
				pixelX = 0;
			}
			else
			{
				if (x >= image.getWidth())
				{
					pixelX = image.getWidth() - 1;
				}
				else
				{
					pixelX = x;
				}

			}

			if (y < 0)
			{
				pixelY = 0;
			}
			else
			{
				if (y >= image.getHeight())
				{
					pixelY = image.getHeight() - 1;
				}
				else
				{
					pixelY = y;
				}
			}

			return image.getPixel(pixelX, pixelY);
		}

		Pixel * getFilteredPixel(const Image & image, int x, int y)
		{
			size_t hMatrix = matrix -> getHeight();
			size_t wMatrix = matrix -> getWidth();

			if (0 == hMatrix % 2 || 0 == wMatrix % 2)
			{
				throw std::runtime_error(WRONG_FILTER_STR);
			}

			float newRed = 0;
			float newGreen = 0;
			float newBlue = 0;

			int widthShift = wMatrix / 2;
			int heightShift = hMatrix / 2;

			for (int i = x - widthShift; i <= x + widthShift; ++i)
			{
				for (int k = y - heightShift; k <= y + heightShift; ++k)
				{
					float matrixPixel = matrix -> getPixel(i - x + widthShift, k - y + heightShift);

					Pixel imagePixel;

					if (i >= 0 && k >= 0 && image.isPointInImage(i, k))
					{
						imagePixel = image.getPixel(i, k);
					}
					else
					{
						imagePixel = getNearPixel(image, i, k);
					}

					long long red = imagePixel.getRed() / div;
					long long green = imagePixel.getGreen() / div;
					long long blue = imagePixel.getBlue() / div;

					newRed+= (red * matrixPixel);
					newGreen+= (green * matrixPixel);
					newBlue+= (blue * matrixPixel);
				}
			}

			if (newRed > 255)
			{
				newRed = 255;
			}

			if (newRed < 0)
			{
				newRed = 0;
			}

			if (newBlue > 255)
			{
				newBlue = 255;
			}

			if (newBlue < 0)
			{
				newBlue = 0;
			}

			if (newGreen > 255)
			{
				newGreen = 255;
			}

			if (newGreen < 0)
			{
				newGreen = 0;
			}

			Pixel * pixel = new Pixel(newRed, newGreen, newBlue);

			return pixel;
		}

	public:

		template <typename...TArgs>
		MatrixFilter(float div, TArgs...args) : div(div)
		{
			matrix = new Matrix(args...);
		}

		virtual Image * apply(const Image & image) override
		{
			pixels.resize(image.getWidth());

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				pixels[i].resize(image.getHeight());
			}

			fillNullptrPixels();

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				for (size_t k = 0; k < image.getHeight(); ++k)
				{
					Pixel * pixel = getFilteredPixel(image, i, k);
					pixels[i][k] = pixel;
				}
			}

			Image * filteredImage = new Image(pixels);
			
			deleteTempPixels();

			return filteredImage;
		}

		MatrixFilter(const MatrixFilter & matrixFilter) = delete;
		MatrixFilter & operator= (const MatrixFilter & matrixFilter) = delete;

		virtual ~MatrixFilter()
		{
			deleteTempPixels();
			delete(matrix);
		}

	};