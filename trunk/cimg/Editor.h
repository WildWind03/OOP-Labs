#pragma once

#include "Image.h"
#include "BaseFilter.h"
#include "AgregateFilter.h"

#include <vector>
#include <memory>

class Editor
{

public:
	Editor();
	Editor (const Editor & editor) = delete;
	Editor & operator= (const Editor & editor) = delete;

	Image * applyFilter (const Image & image, const BaseFilter & filter);
	Image * applyFilters (const Image & image, const std::vector<std::unique_ptr<BaseFilter>> & filters);

	~Editor();
};