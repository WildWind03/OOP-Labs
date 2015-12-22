#include "Editor.h"

Editor::Editor()
{

}

Image Editor::applyFilter(const Image & image, const BaseFilter & filter)
{
	Image filteredImage(image);

	filteredImage = filter.apply(image);

	return filteredImage;
}

Image Editor::applyFilters (const Image & image, const std::vector<std::shared_ptr<BaseFilter>> & filters)
{
	for (size_t i = 0; i < filters.size(); ++i)
	{
		if (nullptr == filters[i])
		{
			throw std::runtime_error(WRONG_FILTER_IN_LIST);
		}
	}
	
	Image newImage(image);

	for (size_t i = 0; i < filters.size(); ++i)
	{
		newImage = filters[i] -> apply(newImage);
	}

	return newImage;
}

Editor::~Editor()
{

}
