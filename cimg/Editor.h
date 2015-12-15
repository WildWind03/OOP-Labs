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

	Image * applyFilter (const Image & image, BaseFilter & filter);
	Image * applyFilters (const Image & image, const std::vector<BaseFilter*> & filters);

	virtual ~Editor();
};