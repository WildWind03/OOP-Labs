#include "Editor.h"

Editor::Editor()
{

}

Image * Editor::applyFilter(const Image & image, BaseFilter & filter)
{
	Image * filteredImage;

	filteredImage = filter.apply(image);

	return filteredImage;
}

Image * Editor::applyFilters (const Image & image, const std::vector<BaseFilter*> & filters)
{
	Image * image1 = nullptr;
	Image * image2 = nullptr;

	if (0 == filters.size())
	{
		Image * filteredImage = new Image(image);
		return filteredImage;
	}

	if (filters.size() > 0)
	{
		image1 = filters[0] -> apply(image);
	}

	if (filters.size() > 1)
	{
		image2 = filters[1] -> apply(*image1);
	}

	for (size_t i = 2; i < filters.size(); ++i)
	{
		switch(i%2)
		{
			case 0 :
			{
				delete(image1);
				image1 = filters[i] -> apply(*image2);
			}
			case 1:
			{
				delete(image2);
				image2 = filters[i] -> apply(*image1);
			}
		}
	}

	if (1 == filters.size() % 2)
	{
		delete(image2);
		return image1;
	}
	else
	{
		delete(image1);
		return image2;
	}
}

Editor::~Editor()
{

}