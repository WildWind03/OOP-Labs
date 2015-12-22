#include "AgregateFilter.h"

AgregateFilter::AgregateFilter(std::vector<std::shared_ptr<BaseFilter>> filters) : filters(filters)
{
	for (size_t i = 0; i < filters.size(); ++i)
	{
		if (nullptr == filters[i])
		{
			throw std::runtime_error(WRONG_FILTER_IN_LIST);
		}
	}
}

Image AgregateFilter::apply(const Image & image) const
{
    Image newImage(image);

	for (size_t i = 0; i < filters.size(); ++i)
	{
		newImage = filters[i] -> apply(newImage);
	}

	return newImage;
}

AgregateFilter::~AgregateFilter()
{

}
