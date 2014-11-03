-module(example1).

main()-> 
  Pidth1 = spawn(example1, th1),
  Pidth2 = spawn(example1, th2),
  Pidth1 ! {Pidth2, cupidsArrow};
  end.

th1()->
	receive
	  {to, cupidsArrow} ->
	    to ! {self(), unrequiitedLove};
	  {From, trueLove} ->
	    io:fwrite("Found true love!\n");
	end.

th2()->
	receive
	  {From, unrequitedLove} ->
	    io:fwrite("Eh...\n");
	end.

fridge1() ->
  receive
    {From, {store, _Food}} ->
      From ! {self(), ok},
      fridge1();
    {From, {take, _Food}} ->
      %% uh....
      From ! {self(), not_found},
      fridge1();
    terminate ->
      ok
  end.
