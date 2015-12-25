#pragma once

#include "BaseFilter.h"
#include "FilterDescription.h"

#include <stdexcept>
#include <memory>

class FilterFactory
{
public:

	static std::shared_ptr<BaseFilter> createFilter(const FilterDescription & filterDescription);
	
};