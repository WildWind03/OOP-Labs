#pragma once

#include "BaseFilter.h"

#include <vector>
#include <stdexcept>
#include <memory>

class AgregateFilter : public BaseFilter
{
	std::vector<BaseFilter*> filters;

	const std::string NO_FILTERS_IN_LIST_STR = "There aren't filters in list!";

public:
	AgregateFilter(std::vector<BaseFilter*> filters);

	AgregateFilter() = delete;
	AgregateFilter(const AgregateFilter & filter) = delete;
	AgregateFilter & operator= (const AgregateFilter & filter) = delete;

	virtual Image * apply(const Image & image) override;

	virtual ~AgregateFilter();
};