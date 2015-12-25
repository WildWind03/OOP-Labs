#include "FilterDescription.h"

void FilterDescription::addParameter(const std::string & parameter)
{
	parameters.push_back(parameter);	
}

FilterDescription::FilterDescription(char filterType) : filterType(filterType)
{
	
}

FilterDescription::FilterDescription(const FilterDescription & filterDesc) 
							: filterType(filterDesc.getFilterType())
{
	parameters = filterDesc.getParameterList();
}

std::vector<std::string> FilterDescription::getParameterList() const
{
	return parameters;
}

char FilterDescription::getFilterType() const
{
	return filterType;
}

FilterDescription::~FilterDescription()
{
	
}