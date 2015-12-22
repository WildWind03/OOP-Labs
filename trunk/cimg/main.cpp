#include "ConsoleParser.h"
#include "Image.h"
#include "FilterDescription.h"
#include "FilterFactory.h"
#include "BaseFilter.h"
#include "BmpLoader.h"
#include "BmpSaver.h"
#include "Editor.h"

#include <vector>
#include <stdexcept>
#include <memory>

int main(int argc, char *argv[])
{
	try
	{
		ConsoleParser parser(argc, argv);

		BmpLoader loader;

		std::vector<std::shared_ptr<BaseFilter>> filters;

		std::vector<FilterDescription> filterDescriptionList = parser.getFilterDescriptionList();

		Image image(loader.load(parser.getInputFilePath()));

		for (size_t i = 0; i < filterDescriptionList.size(); ++i)
		{
			filters.push_back(FilterFactory::createFilter(filterDescriptionList[i]));
		}

		Editor editor;

		Image filteredImage = editor.applyFilters(image, filters);

		BmpSaver saver;

		saver.save(parser.getOutputFilePath(), filteredImage);
	}
	catch (const std::exception & exception)
	{
		std::cout << exception.what() << std::endl;
	}
	
	return 0;
}
