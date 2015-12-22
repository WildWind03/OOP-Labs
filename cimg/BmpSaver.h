#pragma once

#include "ImageSaver.h"
#include "BmpFileHeader.h"
#include "BmpInfoHeader.h"

#include <vector>
#include <fstream>
#include <stdexcept>
#include <memory>

class BmpSaver : public ImageSaver
{
	const std::string OUTPUT_ERROR_STR = "Can't save file!";

	const unsigned short BMP_TYPE = 0x4D42;
	const unsigned int CORE_BMP_SIZE = 12;
	const unsigned int BMP24 = 24;
	const unsigned int ADDITIONAL_BMP_SIZE = 40;
	const unsigned int NO_COMPRESSION = 0;
	const unsigned char COLOR_SIZE = 8;
	const unsigned char BMP_LINE = 4;
	const unsigned char RUBBISH_CHAR = '0';
	const unsigned int BIT_COUNT = 24;

	const unsigned int BYTES_FOR_PIXELS = 3;

	const size_t bitOff = 54;

	template <typename Type>
		void write(std::ofstream & fout, Type & data)
		{
			fout.write(reinterpret_cast<char*>(&data), sizeof(data));
		}

	BmpFileHeader generateBmpFileHeader(const Image & image);
	BmpInfoHeader generateBmpInfoHeader(const Image & image);

public:
	BmpSaver();

	BmpSaver(const BmpSaver & bmpSaver) = delete;
	BmpSaver & operator= (const BmpSaver & bmpSaver) = delete;

	virtual void save(std::string filePath, const Image & image) override;

	virtual ~BmpSaver();
};
