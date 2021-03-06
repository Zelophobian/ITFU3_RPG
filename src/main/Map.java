package main;
public class Map
{
  private final String map; // String of the original Map
  private final int width;  // right and left
  private final int height; // up and down
    
  private char[][] labyrinthMap;  // the map as CharArray with [Y][X]
  
  // TODO: arrange for Array...
  private int sX; // the X of starting point
  private int sY; // the Y of starting point
  
  // TODO: arrange for Array...
  private int mX; // till I get more monster
  private int mY; // till I get more monster
  
  // TODO: arrange for Array...
  private int LastX;  // the X Position I came from
  private int LastY;  // the Y Position I came from
  private int newX; // the X to go to
  private int newY; // the Y to go to
  
  // TODO: arrange for Array...
  private int mLastX; // for monster movment
  private int mLastY;
  private int mNewX;
  private int mNewY;
    
  public Map(String map, int width, int height)
  {
    this.map = map;
    this.width = width;
    this.height = height;
    this.sX = 0;
    this.sY = 0;
    
    this.mX = 0;
    this.mY = 0;
    
    this.buildLabyrinth();

    this.LastY = this.sY;
    this.LastX = this.sX;
    
    this.mLastY = this.mY;
    this.mLastX = this.mX;
  }
  
  private void buildLabyrinth()
  {
    this.labyrinthMap = new char[this.height][this.width];
    for(int y=0; y < this.height; y++)
    {
      for(int x=0; x < this.width; x++)
      {
        char field = map.charAt((y * this.width) + x);
        this.labyrinthMap[y][x] = field;
        if(field == 'P')
        {
          this.sX = x;
          this.sY = y;
        }
        if(field == 'M')
        {
          this.mX = x;
          this.mY = y;
        }
      }
    }
    this.showLabyrinth();
  }
  public void showLabyrinth()
  {
    for(int y=0; y < this.height; y++)
    {
      for(int x=0; x < this.width; x++)
      {
        char field = this.labyrinthMap[y][x];
        if(field == 'S')
        {
          this.sX = x;
          this.sY = y;
        }
        System.out.print(field);
      }
      System.out.print("\n");
    }
  }
  
  public int walkOnMap(String input, int tempMovement, int steps, boolean playerSwitch)
  {
    int l_X;
    int l_Y;
    int n_x;
    int n_y;
    
    if(steps <= tempMovement)
    {
      for(int i=0; i<steps; i++)
      {
// not nice needs a redo
        if(playerSwitch){
          l_X = this.getLastX();
          l_Y = this.getLastY();
          n_x = this.getNewX();
          n_y = this.getNewY();
        }else{
          l_X = this.getmLastX();
          l_Y = this.getmLastY();
          n_x = this.getmNewX();
          n_y = this.getmNewY();
        }
        System.out.println("steps: " + steps + 
            " | tempMovement: " + tempMovement +
            " | x: " + n_x + " | y:" + n_y + " | l_X: " + l_X +
            " | l_Y: " + l_Y
        );
        switch(input)
        {
          case "left":
          this.moveLeft(l_Y, l_X, playerSwitch);
          break;
          case "right":
          this.moveRight(l_Y, l_X, playerSwitch);
          break;
          case "down":
          this.moveDown(l_Y, l_X, playerSwitch);
          break;
          case "up":
          this.moveUp(l_Y, l_X, playerSwitch);
          break;
        }
        
// not nice needs a redo
        if(playerSwitch){
          l_X = this.getLastX();
          l_Y = this.getLastY();
          n_x = this.getNewX();
          n_y = this.getNewY();
        }else{
          l_X = this.getmLastX();
          l_Y = this.getmLastY();
          n_x = this.getmNewX();
          n_y = this.getmNewY();
        }
        
        char mapIndicator = this.labyrinthMap[l_Y][l_X];
        System.out.println("Indicator: " + mapIndicator);
        switch(this.labyrinthMap[n_y][n_x])	  // the field I'm in right now!
        {
          case 'M':
            if(playerSwitch)
            {
              System.out.println("\nYou cannot fill the same space.\n");
              this.resetNewPos(l_Y, l_X, playerSwitch);
            }else{
              this.resetMarkerOnMap(l_Y, l_X);
              this.setMarkerOnMap(n_y, n_x, mapIndicator);
              this.setLastPos(n_y, n_x, playerSwitch);
            }
            break;
          
          case 'P':
            if(!playerSwitch)
            {
              System.out.println("\nYou cannot fill the same space.\n");
              this.resetNewPos(l_Y, l_X, playerSwitch);
            }else{
              this.resetMarkerOnMap(l_Y, l_X);
              this.setMarkerOnMap(n_y, n_x, mapIndicator);
              this.setLastPos(n_y, n_x, playerSwitch);
            }
            break;
            
          case ' ':
            this.resetMarkerOnMap(l_Y, l_X);
            this.setMarkerOnMap(n_y, n_x, mapIndicator);
            this.setLastPos(n_y, n_x, playerSwitch);
            break;
            
          case '#':
             if( !playerSwitch )
             {
               // AI behavior
             }
            break;
          default:
        }
        showLabyrinth();
        tempMovement--;
      }
    }
    else
    {
      System.out.println("That is " + (steps-tempMovement) + " too far.");
    }
    return tempMovement;
  }
  
  public int getDistance()
  {
    int x = Math.abs(this.getLastX() - this.getmX());
    int y = Math.abs(this.getLastY() - this.getmY());
    return Math.round( (float) Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) ) );
  }
  
  //##### MOVEMENT #####
  private void moveUp(int y, int x, boolean playerSwitch)		// D: 0
  {
	//UP == Y--
    if(playerSwitch)
    {
      this.setNewY(y-1);
      this.setNewX(x);
    }else{
      this.setmNewY(y-1);
      this.setmNewX(x);
    }
  }
  private void moveRight(int y, int x, boolean playerSwitch)	// D: 1
  {
	//Right == x++
    if(playerSwitch)
    {
      this.setNewY(y);
      this.setNewX(x+1);
    }else{
      this.setmNewY(y);
      this.setmNewX(x+1);
    }
  }
  private void moveDown(int y, int x, boolean playerSwitch)	// D: 2
  {
	// DOWN == Y++
    if(playerSwitch)
    {
      this.setNewY(y+1);
      this.setNewX(x);
    }else{
      this.setmNewY(y+1);
      this.setmNewX(x);
    }
  }
  private void moveLeft(int y, int x, boolean playerSwitch)	// D: 3
  {
	// LEFT == x--
    if(playerSwitch)
    {
      this.setNewY(y);
      this.setNewX(x-1);
    }else{
      this.setmNewY(y);
      this.setmNewX(x-1);
    }
  }
  private void setLastPos(int y, int x, boolean playerSwitch)
  {
    if(playerSwitch)
    {
      this.setLastY(y);
      this.setLastX(x);
    }else{
      this.setmLastY(y);
      this.setmLastX(x);
    }
  }
  private void resetNewPos(int y, int x, boolean playerSwitch)
  {
    if(playerSwitch)
    {
      
    }else{
      
    }
  }
  
  // ##### MARKERS ON THE MAP #####
  public void setMarkerOnMap(int y, int x, char mark)
  {
    this.labyrinthMap[y][x] = mark;
  }
  private void resetMarkerOnMap(int y, int x)
  {
    this.labyrinthMap[y][x] = ' ';
  }
    
  //##### POSITION PLAYER #####
  public int getSX(){
    return this.sX;
  }
  public int getSY(){
    return this.sY;
  }
  public int getLastX(){
    return this.LastX;
  }
  public int getLastY(){
    return this.LastY;
  }
  public void setLastX(int LastX) {
    this.LastX = LastX;
  }
  public void setLastY(int LastY) {
    this.LastY = LastY;
  }
  public int getNewX() {
    return newX;
  }
  public void setNewX(int newX) {
    this.newX = newX;
  }
  public int getNewY() {
    return newY;
  }
  public void setNewY(int newY) {
    this.newY = newY;
  }
  
  //##### POSITION MONSTER #####
  public int getmX() {
    return mX;
  }
  public int getmY() {
    return mY;
  }
  public int getmLastX() {
    return mLastX;
  }
  public void setmLastX(int mLastX) {
    this.mLastX = mLastX;
  }
  public int getmLastY() {
    return mLastY;
  }
  public void setmLastY(int mLastY) {
    this.mLastY = mLastY;
  }
  public int getmNewX() {
    return mNewX;
  }
  public void setmNewX(int mNewX) {
    this.mNewX = mNewX;
  }
  public int getmNewY() {
    return mNewY;
  }
  public void setmNewY(int mNewY) {
    this.mNewY = mNewY;
  }

 
}