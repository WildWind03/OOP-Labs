#pragma once

#include "BaseFilter.h"

#include <vector>
#include <stdexcept>
#include <memory>

class AgregateFilter : public BaseFilter
{
	std::vector<std::shared_ptr<BaseFilter>> filters;

	const std::string WRONG_FILTER_IN_LIST = "Invalid filter!";

public:
	AgregateFilter(std::vector<std::shared_ptr<BaseFilter>> filters);

	AgregateFilter() = delete;
	AgregateFilter(const AgregateFilter & filter) = delete;
	AgregateFilter & operator= (const AgregateFilter & filter) = delete;
	AgregateFilter (AgregateFilter && filter) = default;

	virtual Image apply(const Image & image) const override;

	virtual ~AgregateFilter();
};
