#include "ShotParser.h"

ShotParser::ShotParser(std::string str) : BaseParser(str) 
{

}

ShotPoint ShotParser::parse()
{
	if (str == exitStr)
	{
		throw GameExitException(exitStr);
	}

	if (str.size() < minLengthOfCorrectString)
	{
		throw InvalidInputException(tooShortError);
	}

	char x = str[0];

	std::string y = str.substr(1);

	size_t h, w;

	h = getNumFromStr(y);
	w = getNumByChar(x);

	return ShotPoint(h, w);
}

ShotParser::~ShotParser() 
{

}