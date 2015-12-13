#include "Editor.h"

Editor::Editor()
{

}

Image * Editor::applyFilter(const Image & image, const BaseFilter & filter)
{
	Image * filteredImage;

	filteredImage = filter.apply(image);

	return filteredImage;
}

Image * Editor::applyFilters (const Image & image, const std::vector<std::unique_ptr<BaseFilter>> & filters)
{
	AgregateFilter filter(filters);

	Image * filteredImage = filter.apply(image);

	return filteredImage;
}

Editor::~Editor()
{

}