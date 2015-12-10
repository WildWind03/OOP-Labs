#pragma once

#include "FilterType.h"

#include <string>

class FilterDescryption
{
	std::string descryption;
	
	FilterType filterType;

public:

	FilterDescryption() = delete;
	FilterDescryption(FilterType filterType, std::string descryption = "NO_ARGUMENT");
	FilterDescryption(const FilterDescryption & filterDesc);
	FilterDescryption & operator= (const FilterDescryption & filterDesc) = delete;

	std::string getDescryption() const;

	FilterType getFilterType() const;

	~FilterDescryption();
};