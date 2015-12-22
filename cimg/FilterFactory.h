#pragma once

#include "BaseFilter.h"
#include "OnePixelFilter.h"
#include "FilterDescription.h"
#include "FilterType.h"
#include "NegativeFunctor.h"
#include "GrayscaleFunctor.h"
#include "CutFilter.h"
#include "MatrixFilter.h"
#include "SharpMatrix.h"
#include "EdgeMatrix.h"
#include "BlurMatrix.h"
#include "AgregateFilter.h"
#include "EdgeFunctor.h"
#include "MotionBlurFilter.h"


#include <string>
#include <sstream>
#include <iostream>
#include <stdexcept>
#include <memory>

class FilterFactory
{
public:

	static std::shared_ptr<BaseFilter> createFilter(const FilterDescription & filterDescription);
	
};