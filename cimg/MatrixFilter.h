#pragma once

#include "BaseFilter.h"
#include "Image.h"
#include "Pixel.h"
#include "SharpMatrix.h"

#include <vector>
#include <stdexcept>
#include <iostream>

template <typename Matrix>
	class MatrixFilter : public BaseFilter
	{
		Matrix * matrix;

		const std::string WRONG_FILTER_STR = "Unworking filter!";

		const unsigned char MAX_COLOR = 255;
		const unsigned char MIN_COLOR = 0;

		Pixel getNearPixel(const Image & image, int x, int y) const
		{
			size_t pixelX;
			size_t pixelY;

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

		Pixel getFilteredPixel(const Image & image, int x, int y) const
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

			int widthShift = static_cast <int>(wMatrix / 2);
			int heightShift = static_cast <int>(hMatrix / 2);

			for (int i = x - widthShift; i <= x + widthShift; ++i)
			{
				for (int k = y - heightShift; k <= y + heightShift; ++k)
				{
					float matrixPixel = matrix -> getPixel(i - x + widthShift, k - y + heightShift);

					Pixel imagePixel;

					if (image.isPointInImage(i, k))
					{
						imagePixel = image.getPixel(i, k);
					}
					else
					{
						imagePixel = getNearPixel(image, i, k);
					}

					float red = static_cast <float> (imagePixel.getRed());
					float green = static_cast <float> (imagePixel.getGreen());
					float blue = static_cast <float> (imagePixel.getBlue());

					newRed += red * matrixPixel;
					newGreen += green * matrixPixel;
					newBlue += blue * matrixPixel;
				}
			}

			if (newRed > MAX_COLOR)
			{
				newRed = MAX_COLOR;
			}

			if (newRed < MIN_COLOR)
			{
				newRed = MIN_COLOR;
			}

			if (newBlue > MAX_COLOR)
			{
				newBlue = MAX_COLOR;
			}

			if (newBlue < MIN_COLOR)
			{
				newBlue = MIN_COLOR;
			}

			if (newGreen > MAX_COLOR)
			{
				newGreen = MAX_COLOR;
			}

			if (newGreen < MIN_COLOR)
			{
				newGreen = MIN_COLOR;
			}

			return Pixel(static_cast<unsigned char> (newRed), static_cast<unsigned char> (newGreen), static_cast<unsigned char> (newBlue));
		}

	public:

		template <typename...TArgs>
			MatrixFilter(TArgs...args)
			{
				matrix = new Matrix(args...);
			}

		virtual Image apply(const Image & image) const override
		{

			std::vector<std::vector<Pixel>> pixels;

			pixels.resize(image.getWidth());

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				pixels[i].resize(image.getHeight());
			}

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				for (size_t k = 0; k < image.getHeight(); ++k)
				{
					pixels[i][k] = getFilteredPixel(image, i, k);
				}
			}

			Image filteredImage(pixels);

			return filteredImage;
		}

		MatrixFilter(const MatrixFilter & matrixFilter) = delete;
		MatrixFilter & operator= (const MatrixFilter & matrixFilter) = delete;

		virtual ~MatrixFilter()
		{
			delete(matrix);
		}

	};
