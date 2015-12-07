#include "Statistics.h"

Statistics::Statistics() : g1Vic(0), g2Vic(0) 
{

}

void Statistics::addG1Vic()
{
	++g1Vic;
}
void Statistics::addG2Vic()
{
	++g2Vic;
}

size_t Statistics::getG1Vic() const
{
	return g1Vic;
}
size_t Statistics::getG2Vic() const
{
	return g2Vic;
}

size_t Statistics::getCountOfGames() const
{
	return g1Vic + g2Vic;
}

Statistics::~Statistics() 
{

}