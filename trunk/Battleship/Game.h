#pragma once 

#include "GameConf.h"
#include "GamerFactory.h"

class Game : public Subject
{

	/*class EndOfGameHandler
	{
	public:
		virtual void OnGameEnded() = 0;
	};
	*/

	//EndOfGameHandler *eogh;

	Field *fField.
	Field *sField;

	Gamer *fGamer;
	Gamer *sGamer;

	size_t currentRound;

public:

	Game(GameConf *conf);

	void init();

	void begin();

	~Game();
};