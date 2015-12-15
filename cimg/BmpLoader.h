#pragma once

#include "ImageLoader.h"
#include "Image.h"
#include "BmpFileHeader.h"
#include "BmpInfoHeader.h"

#include <fstream>
#include <stdexcept>
#include <iostream>

class BmpLoader : public ImageLoader
{
	const unsigned short BMP = 0x4D42;
	const unsigned int CORE_BMP_SIZE = 12;
	const unsigned int BMP24 = 24;
	const unsigned int ADDITIONAL_BMP_SIZE = 40;
	const unsigned int LIMIT_BMP_SIZE = 52;
	const unsigned int NO_COMPRESSION = 0;
	const unsigned char COLOR_SIZE = 8;
	const unsigned char BMP_LINE = 4;

	const std::string NO_FILE_STR = "There isn't file with such name!";
	const std::string NO_BMP_STR = "It is not bmp file!";
	const std::string NO_BMP24_STR = "It is not BMP24 file!";
	const std::string COMPRESSED_FILE_STR = "It's impossible to open compressed bmp file";
	const std::string TOO_DIFFICULT_FORMAT_STR = "Too difficult formam! Error!";

	template <typename Type>
		void read(std::ifstream & fin, Type & result, size_t size)
		{
			fin.read(reinterpret_cast<char*>(&result), size);
		}

	std::vector<std::vector<Pixel*>> pixels;

	void clearTempPixels();
	void fillNullptrPixels();

public:
	BmpLoader();

	BmpLoader (const BmpLoader & bmpLoader) = delete;
	BmpLoader & operator= (const BmpLoader & bmpLoader) = delete;

	virtual Image * load(std::string filePath) override;

	virtual ~BmpLoader();
};
