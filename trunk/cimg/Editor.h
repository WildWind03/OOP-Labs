#pragma once

#include "Image.h"
#include "BaseFilter.h"
#include "AgregateFilter.h"

#include <vector>
#include <memory>
#include <string>

class Editor
{
	const std::string WRONG_FILTER_IN_LIST = "Trying to apply invalid filter!";

public:
	Editor();
	Editor (const Editor & editor) = delete;
	Editor & operator= (const Editor & editor) = delete;

	Image applyFilter (const Image & image, const BaseFilter & filter);
	Image applyFilters (const Image & image, const std::vector<std::shared_ptr<BaseFilter>> & filters);

	virtual ~Editor();
};