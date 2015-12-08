#include "FilterDescryption.h"

FilterDescryption::FilterDescryption(FilterType filterType, std::string descryption)
						    : filterType(filterType), descryption(descryption) 
{

}

FilterDescryption::FilterDescryption(const FilterDescryption & filterDesc) 
							: filterType(filterDesc.getFilterType()), descryption(filterDesc.getDescryption())
{

}

std::string FilterDescryption::getDescryption() const
{
	return descryption;
}

FilterType FilterDescryption::getFilterType() const
{
	return filterType;
}

FilterDescryption::~FilterDescryption()
{
	
}