#include "BaseParser.h"

BaseParser::BaseParser(std::string str) : str(str)
{

}

size_t BaseParser::getNumFromStr(std::string str) const
{
	size_t h;

	for (size_t i = 0; i < str.size(); ++i)
	{
		if ((str[i] < '0') || (str[i] > '9'))
		{
			throw InvalidInputException(incorrectInputStr);
		}
	}
		
	h = std::stoi(str);

	return h;
}

size_t BaseParser::getNumByChar(char c) const
{
	if (c < posOfaInAscii || c > posOfzInAscii)
	{
		throw InvalidInputException(incorrectInputStr);
	}

	size_t w = (size_t) (c - posOfaInAscii);

	return w;
}

void BaseParser::takeNewString(std::string str)
{
	this -> str = str;
}

BaseParser::~BaseParser()
{

}