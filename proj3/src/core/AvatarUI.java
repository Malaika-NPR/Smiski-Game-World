package core;

import tileengine.TETile;
import tileengine.Tileset;

public class AvatarUI {
    private int AvatarX;
    private int AvatarY;
    private TETile[][] world;

    public AvatarUI(TETile[][] world, int AvatarX, int AvatarY) {
        this.world = world;
        this.AvatarX = AvatarX;
        this.AvatarY = AvatarY;
        world[AvatarX][AvatarY] = Tileset.AVATAR;
    }
    private void currentAvatar(int x, int y) {
        if (world[x][y] == null) {
            world[x][y] = Tileset.AVATAR;
        }
        if (world[x][y] != null) {
            world[x][y] = Tileset.NOTHING;
        }
    }
    //public void avatarInteractions(){
        //char keyInput = direction;
        //if (keyInput == 'W') {
        //move up
        //if (keyInput == 'A') {
        //move left
        //if (keyInput == 'S') {
        //move down
        //if (keyInput == 'D') {
        //move right
    }
    //public saveBoard() {

    //public loadBoard() {

   // }
   // }




