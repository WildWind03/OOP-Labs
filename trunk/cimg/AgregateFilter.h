#pragma once

#include "BaseFilter.h"

#include <vector>
#include <stdexcept>
#include <memory>

class AgregateFilter : BaseFilter
{
	const std::vector<std::unique_ptr<BaseFilter>> & filters;

	const std::string NO_FILTERS_IN_LIST_STR = "There aren't filters in list!";

public:
	AgregateFilter(const std::vector<std::unique_ptr<BaseFilter>> & filters);

	AgregateFilter(const AgregateFilter & filter) = delete;
	AgregateFilter & operator= (const AgregateFilter & filter) = delete;

	virtual Image * apply(const Image & image) const override;

	virtual ~AgregateFilter();
};