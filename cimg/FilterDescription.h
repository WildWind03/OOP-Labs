#pragma once

#include <string>
#include <vector>

class FilterDescription
{
	char filterType;
	std::vector <std::string> parameters;

public:

	FilterDescription() = delete;
	FilterDescription(char filterType);
	FilterDescription(const FilterDescription & filterDesc);
	FilterDescription & operator= (const FilterDescription & filterDesc) = delete;

	std::vector<std::string> getParameterList() const;

	void addParameter(const std::string & parameter);

	char getFilterType() const;

	virtual ~FilterDescription();
};