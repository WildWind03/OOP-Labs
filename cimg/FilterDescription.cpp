#include "FilterDescription.h"

FilterDescription::FilterDescription(char filterType, std::string description)
						    : filterType(filterType)
{
	std::istringstream sout(description);

	std::string temp;

	while(!sout.eof())
	{
		sout >> temp;
		parameters.push_back(temp);
	}	
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