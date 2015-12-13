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
	ConsoleParser parser(argc, argv);

	BmpLoader loader;
	std::unique_ptr <Image> image(loader.load("./nsu.bmp"));

	std::vector<std::unique_ptr<BaseFilter>> filters;

	std::vector<FilterDescription> filterDescriptionList = parser.getFilterDescriptionList();

	for (size_t i = 0; i < filterDescriptionList.size(); ++i)
	{
		filters.push_back(std::unique_ptr<BaseFilter>(FilterFactory::createFilter(filterDescriptionList[i])));
	}

	Editor editor;
	std::unique_ptr<Image> filteredImage(editor.applyFilters(*image, filters));

	BmpSaver saver;
	saver.save("./NSU4.bmp", *filteredImage);

	return 0;
}
