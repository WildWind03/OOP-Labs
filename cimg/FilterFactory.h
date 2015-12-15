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
#include "MotionBlurMatrix.h"


#include <string>
#include <sstream>
#include <iostream>
#include <stdexcept>

class FilterFactory
{
public:

	static BaseFilter * createFilter(const FilterDescription & filterDescription);
	
};