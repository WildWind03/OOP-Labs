#pragma once

#include <string>
#include <sstream>
#include <vector>

class FilterDescription
{
	char filterType;
	std::vector <std::string> parameters;

	const std::string NO_ARGUMENT_STR = "NO_ARGUMENT";

public:

	FilterDescription() = delete;
	FilterDescription(char filterType, std::string description = "NO_ARGUMENT");
	FilterDescription(const FilterDescription & filterDesc);
	FilterDescription & operator= (const FilterDescription & filterDesc) = delete;

	std::vector<std::string> getParameterList() const;

	char getFilterType() const;

	~FilterDescription();
};