#include "FilterFactory.h"

#include "NegativeFunctor.h"
#include "GrayscaleFunctor.h"
#include "CutFilter.h"
#include "MatrixFilter.h"
#include "SharpMatrix.h"
#include "EdgeMatrix.h"
#include "BlurMatrix.h"
#include "AgregateFilter.h"
#include "EdgeFunctor.h"
#include "MotionBlurFilter.h"
#include "OnePixelFilter.h"

#include <string>

std::shared_ptr<BaseFilter> FilterFactory::createFilter(const FilterDescription & filterDescription)
{
	char filterType = filterDescription.getFilterType();

	switch (filterType)
	{
		case 'c' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();

			if (2 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			int newWidth, newHeight;
			
			try
			{
				newWidth = std::stoi(param[0]);
				newHeight = std::stoi(param[1]);

				if (newWidth < 0 || newHeight < 0)
				{
					throw std::invalid_argument("");
				}
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> cutFilter (new CutFilter(static_cast<size_t> (newWidth), static_cast<size_t> (newHeight)));

			return cutFilter;
		}
		case 'n' :
		{
			std::shared_ptr<BaseFilter> negativeFilter (new OnePixelFilter<NegativeFunctor>);

			return negativeFilter;
		}
		case 'g' :
		{
			std::shared_ptr<BaseFilter> grayscaleFilter (new OnePixelFilter<GrayscaleFunctor>);

			return grayscaleFilter;
		}
		case 's' :
		{
			std::shared_ptr<BaseFilter> sharpFilter (new MatrixFilter<SharpMatrix>(3));

			return sharpFilter;
		}
		case 'e' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();

			if (1 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			int threshold;

			try
			{
				threshold = std::stoi(param[0]);

				if (threshold < 0 || threshold > 255)
				{
					throw std::invalid_argument("");
				}
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> negativeFilter (new OnePixelFilter<NegativeFunctor>);
			std::shared_ptr<BaseFilter> edgeMatrixFilter (new MatrixFilter<EdgeMatrix>(3));
			std::shared_ptr<BaseFilter> edgeFuctorFilter (new OnePixelFilter<EdgeFunctor>(static_cast <unsigned char> (threshold)));

			std::vector <std::shared_ptr<BaseFilter>> filters;

			filters.push_back(negativeFilter);
			filters.push_back(edgeMatrixFilter);
			filters.push_back(edgeFuctorFilter);

			std::shared_ptr<BaseFilter> edgeFilter (new AgregateFilter(filters));

			return edgeFilter;
		}
		case 'b' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();
			
			if (1 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			float sigma;

			try
			{
				sigma = std::stof(param[0]);

				if (sigma < 0)
				{
					throw std::invalid_argument("");
				}
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> blurFilter (new MatrixFilter<BlurMatrix>(sigma, 5));

			return blurFilter;
		}
		case 'm' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();
			
			if (2 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			int speed;
			int angle;

			try
			{
				angle = std::stoi(param[0]);
				speed = std::stoi(param[1]);

				if (angle < 0 || speed < 0)
				{
					throw std::invalid_argument("");
				}
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> motionFilter (new MotionBlurFilter(static_cast<size_t>(angle), static_cast<size_t>(speed)));

			return motionFilter;
		}
	}

	throw std::invalid_argument("Can't create filter. Wrong parameters");
}