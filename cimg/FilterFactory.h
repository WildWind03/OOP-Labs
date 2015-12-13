#pragma once

#include "BaseFilter.h"
#include "OnePixelFilter.h"
#include "FilterDescription.h"
#include "FilterType.h"
#include "NegativeFunctor.h"

#include <string>

class FilterFactory
{

public:

	static BaseFilter * createFilter(const FilterDescription & filterDescription);

};