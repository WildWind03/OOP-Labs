#pragma once

#include "BaseFilter.h"

template <class Functor>
	class OnePixelFilter : public BaseFilter
	{
		Functor * functor;

	public:

		OnePixelFilter(const OnePixelFilter & onePixelFilter) = delete;
		OnePixelFilter & operator= (const OnePixelFilter & onePixelFilter) = delete;

		template <typename...TArgs>
			OnePixelFilter(TArgs...args)
			{
				functor = new Functor(args...);
			}

		virtual Image apply(const Image & image) const override
		{
			std::vector <std::vector<Pixel>> filteredImage;

			filteredImage.resize(image.getWidth());

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				filteredImage[i].resize(image.getHeight());
			}

			for (size_t i = 0; i < image.getWidth(); ++i)
			{
				for (size_t k = 0; k < image.getHeight(); ++k)
				{
					Pixel pixel = image.getPixel(i, k);
					filteredImage[i][k] = (*functor)(pixel);
				}
			}

			return Image(filteredImage);
		}

		virtual ~OnePixelFilter()
		{
			delete(functor);
		}

	};
