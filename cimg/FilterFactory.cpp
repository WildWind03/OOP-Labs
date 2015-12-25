#include "FilterFactory.h"

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

			size_t newWidth, newHeight;
			
			try
			{
				newWidth = std::stoi(param[0]);
				newHeight = std::stoi(param[1]);
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> cutFilter (new CutFilter(newWidth, newHeight));

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
			std::shared_ptr<BaseFilter> sharpFilter (new MatrixFilter<SharpMatrix>);

			return sharpFilter;
		}
		case 'e' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();

			if (1 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			int parameter;

			try
			{
				parameter = std::stoi(param[0]);
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> negativeFilter (new OnePixelFilter<NegativeFunctor>);
			std::shared_ptr<BaseFilter> edgeMatrixFilter (new MatrixFilter<EdgeMatrix>);
			std::shared_ptr<BaseFilter> edgeFuctorFilter (new OnePixelFilter<EdgeFunctor>(parameter));

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
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> blurFilter (new MatrixFilter<BlurMatrix>(sigma));

			return blurFilter;
		}
		case 'm' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();
			
			if (2 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			size_t speed;
			size_t angle;

			try
			{
				angle = std::stoi(param[0]);
				speed = std::stoi(param[1]);
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			std::shared_ptr<BaseFilter> motionFilter (new MotionBlurFilter(angle, speed));

			return motionFilter;
		}
	}

	throw std::invalid_argument("Can't apply filter. Wrong parameters");
}