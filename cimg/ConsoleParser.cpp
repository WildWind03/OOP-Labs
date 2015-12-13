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

                if ('h' == c)
                {
                        std::cout << helpStr << std::endl;
                        exit(0);      
                }

                if (nullptr == optarg)
                {
                        FilterDescription filterDescription(c);
                        filtersDescList.push_back(filterDescription);
                }
                else
                {
                        FilterDescription filterDescription(c, optarg);
                        filtersDescList.push_back(filterDescription);
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

std::vector <FilterDescription> ConsoleParser::getFilterDescriptionList()
{
	return filtersDescList;
}

ConsoleParser::~ConsoleParser()
{

}