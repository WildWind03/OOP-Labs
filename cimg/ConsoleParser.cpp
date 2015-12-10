#include "ConsoleParser.h"

ConsoleParser::ConsoleParser(int argc, char *argv[])
{
	int c;

	while(true)
	{
	    static struct option long_options[] =
        {
	        {"crop",  required_argument, 0, 'c'},
	        {"gs",    no_argument,       0, 'g'},
	        {"neg",   no_argument,       0, 'n'},
	        {"blur",  required_argument, 0, 'b'},
	        {"sharp", no_argument,       0, 's'},
	        {"edge",  required_argument, 0, 'e'},
	        {"my",    required_argument, 0, 'm'},
	        {"help",  no_argument,       0, 'h'},
        }; 

        int option_index = 0;

        c = getopt_long (argc, argv, "gnshc:b:e:m:", long_options, &option_index);

        if (c == -1)
        {
        	break;
        }

        switch(c)
        {
        	case 'c':
        	{
        		FilterDescryption filterDescryption(FilterType::CROP, optarg);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'g':
        	{
        		FilterDescryption filterDescryption(FilterType::GRAYSCALE);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'n'  :
        	{
        		FilterDescryption filterDescryption(FilterType::NEGATIVE);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'b' :
        	{
        		FilterDescryption filterDescryption(FilterType::GAUSSIAN_BLUR, optarg);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 's' :
        	{
        		FilterDescryption filterDescryption(FilterType::SHARPENING);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'e' :
        	{
        		FilterDescryption filterDescryption(FilterType::EDGE_DETECTION, optarg);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'm' :
        	{
        		FilterDescryption filterDescryption(FilterType::MINE, optarg);
        		filtersDescList.push_back(filterDescryption);
        		break;
        	}

        	case 'h' :
        	{
        		std::cout << helpStr << std::endl;
        		exit(0);
        		break;
        	}

        }
	}	
}

std::string ConsoleParser::getInputFilePath() const
{
	return inputFilePath;
}

std::string ConsoleParser::getOutputFilePath() const
{
	return outputFilePath;
}

FilterDescryption ConsoleParser::getNextFilterDescryption()
{
	if (filtersDescList.empty())
	{
		throw std::range_error(emptyFilterListStr);
	}

	FilterDescryption filterDesc = filtersDescList.front();
	filtersDescList.pop_front();

	return filterDesc;
}

ConsoleParser::~ConsoleParser()
{

}