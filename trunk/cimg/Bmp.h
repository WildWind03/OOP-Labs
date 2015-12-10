#pragma once

#include "Pixel.h"
#include "BmpHeader.h"

#include <string>
#include <fstream>
#include <stdexcept>
#include <vector>
#include <iostream>

class Bmp
{
	const unsigned short BMP = 0x4D42;
	const unsigned int CORE_BMP_SIZE = 12;
	const unsigned int BMP24 = 24;
	const unsigned int ADDITIONAL_BMP_SIZE = 40;
	const unsigned int NO_COMPRESSION = 0;
	const unsigned char COLOR_SIZE = 8;
	const unsigned char BMP_LINE = 4;
	const unsigned char RUBBISH_CHAR = '0';

	const std::string NO_FILE_STR = "There isn't file with such name!";
	const std::string OUTPUT_ERROR_STR = "Can't save file!";
	const std::string NO_BMP_STR = "It is not bmp file!";
	const std::string NO_BMP24_STR = "It is not BMP24 file!";
	const std::string COMPRESSED_FILE_STR = "It's impossible to open compressed bmp file";

	size_t height;
	size_t width;

	std::vector<std::vector<Pixel*>> pixels;

	BmpHeader bmpHeader;

	const std::string outputFileName;

	template <typename Type>
		void read(std::ifstream & fin, Type & result, size_t size)
		{
			fin.read(reinterpret_cast<char*>(&result), size);
		}

	template <typename Type>
		void write(std::ofstream & fout, Type & data)
		{
			fout.write(reinterpret_cast<char*>(&data), sizeof(data));
		}

	void save();

public:
	Bmp(std::string inputFileName, std::string outputFileName);

	Bmp(const Bmp & bmp) = delete;
	Bmp & operator= (const Bmp & bmp) = delete;

	Pixel * getPixel(size_t x, size_t y);

	size_t getHeight() const;
	size_t getWidth() const;

	~Bmp();
};	