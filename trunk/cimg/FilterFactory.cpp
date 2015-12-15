#include "FilterFactory.h"

BaseFilter * FilterFactory::createFilter(const FilterDescription & filterDescription)
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

			CutFilter * cutFilter = new CutFilter(newWidth, newHeight);

			return cutFilter;
		}
		case 'n' :
		{
			OnePixelFilter<NegativeFunctor> * negativeFilter = new OnePixelFilter<NegativeFunctor>;

			return negativeFilter;
		}
		case 'g' :
		{
			OnePixelFilter<GrayscaleFunctor> * grayscaleFilter = new OnePixelFilter<GrayscaleFunctor>;

			return grayscaleFilter;
		}
		case 's' :
		{
			float div = 1;

			MatrixFilter<SharpMatrix> * sharpFilter = new MatrixFilter<SharpMatrix>(div);

			return sharpFilter;
		}
		case 'e' :
		{
			std::vector <std::string> param = filterDescription.getParameterList();

			if (1 != param.size())
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			size_t parameter;

			try
			{
				parameter = std::stoi(param[0]);
			}
			catch (const std::exception & er)
			{
				throw std::invalid_argument("Can't apply filter. Wrong parameters");
			}

			float div = 1;

			OnePixelFilter<NegativeFunctor> * negativeFilter = new OnePixelFilter<NegativeFunctor>;
			MatrixFilter<EdgeMatrix> * edgeMatrixFilter = new MatrixFilter<EdgeMatrix>(div);
			OnePixelFilter<EdgeFunctor> * edgeFuctorFilter = new OnePixelFilter<EdgeFunctor>(parameter);

			std::vector <BaseFilter*> filters;

			filters.push_back(negativeFilter);
			filters.push_back(edgeMatrixFilter);
			filters.push_back(edgeFuctorFilter);

			AgregateFilter * edgeFilter = new AgregateFilter(filters);

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

			float div = 1;

			MatrixFilter<BlurMatrix> * blurFilter = new MatrixFilter<BlurMatrix>(div, sigma);

			return blurFilter;
		}
		case 'm' :
		{
			float div = 9;

			MatrixFilter<MotionBlurMatrix> * motionFilter = new MatrixFilter<MotionBlurMatrix>(div);

			return motionFilter;
		}
	}

	return nullptr;
}