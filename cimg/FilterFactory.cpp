#include "FilterFactory.h"

BaseFilter * FilterFactory::createFilter(const FilterDescription & filterDescription)
{
	char filterType = filterDescription.getFilterType();

	switch (filterType)
	{
		case 'n' :
		{
			OnePixelFilter<NegativeFunctor> * negativeFilter = new OnePixelFilter<NegativeFunctor>;

			return negativeFilter;
		}
	}

	return nullptr;
}