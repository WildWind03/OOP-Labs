#pragma once

#include <string>
#include <sstream>
#include <vector>
#include <iostream>

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

	void addParameter(std::string parameter);

	char getFilterType() const;

	virtual ~FilterDescription();
};