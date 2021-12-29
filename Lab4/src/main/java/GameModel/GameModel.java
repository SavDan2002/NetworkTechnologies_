package GameModel;

import NameLater.DirectionM;
import NameLater.GameConfigM;
import NameLater.GamePlayerM;
import NameLater.GameStateM.CoordM;
import NameLater.GameStateM.GameStateM;
import NameLater.GameStateM.SnakeM;

import java.util.*;

public class GameModel {

    private class KillEvent{
        int deadId, killerId;
        public KillEvent(int deadId, int killerId){
            this.deadId = deadId;
            this.killerId = killerId;
        }
    }

    public static class GameModelMessage {
        private List<SnakeM> snakes;
        private GameConfigM gameConfig;
        private List<GamePlayerM> players;
        private List<CoordM> foods;
        private int stateOrder;

        private GameModelMessage(List<SnakeM> snakes, GameConfigM gameConfig, List<GamePlayerM> players, List<CoordM> foods, int stateOrder) {
            this.snakes = snakes;
            this.gameConfig = gameConfig;
            this.players = players;
            this.foods = foods;
            this.stateOrder = stateOrder;
        }

        public List<SnakeM> getSnakes() {
            return snakes;
        }

        public void setSnakes(List<SnakeM> snakes) {
            this.snakes = snakes;
        }

        public GameConfigM getGameConfig() {
            return gameConfig;
        }

        public void setGameConfig(GameConfigM gameConfig) {
            this.gameConfig = gameConfig;
        }

        public List<GamePlayerM> getPlayers() {
            return players;
        }

        public void setPlayers(List<GamePlayerM> players) {
            this.players = players;
        }

        public List<CoordM> getFoods() {
            return foods;
        }

        public void setFoods(List<CoordM> foods) {
            this.foods = foods;
        }

        public int getStateOrder() {
            return stateOrder;
        }

        public void setStateOrder(int stateOrder) {
            this.stateOrder = stateOrder;
        }
    }

    private Map<Integer, SnakeM> snakes;
    private Map<Integer, DirectionM> nextDirection;
    private Map<Integer, GamePlayerM> players;
    private GameConfigM config;
    private List<CoordM> foods;
    private List<KillEvent> snakesToKill;
    private int[][] field;
    private int stateOrder;

    public GameModel(GameConfigM config){
        this.snakes = new HashMap<>();
        this.config = config;
        this.players = new HashMap<>();
        foods = new ArrayList<>();
        field = new int[config.getWidth()][config.getHeight()];
        generateFood(config.getFoodStatic());
        nextDirection = new HashMap<>();
        stateOrder = 0;
    }

    public GameModel(GameStateM state){
        this.config = state.getConfig();
        this.snakes = new HashMap<>();
        state.getSnakes().forEach(snake -> snakes.put(snake.getPlayerId(), snake));
        foods = new ArrayList<>(state.getFoods());
        nextDirection = new HashMap<>();
        players = new HashMap<>();
        state.getPlayers().getPlayers().forEach(player -> players.put(player.getId(), player));
        field = new int[config.getWidth()][config.getHeight()];
        stateOrder = 0;
        updateField();
    }

    public synchronized GameModelMessage getMessage(){
        List<SnakeM> snakes = new ArrayList<>(this.snakes.values());
        List<GamePlayerM> players = new ArrayList<>(this.players.values());
        return new GameModelMessage(snakes, config, players, foods, stateOrder);
    }

    public GameConfigM getConfig(){
        return config;
    }

    public synchronized List<GamePlayerM> getPlayers(){
        return players.values().stream().toList();
    }

    private synchronized void updateField() {
        field = new int[config.getWidth()][config.getHeight()];
        snakes.forEach((id, snake) -> {
            int x = snake.getPoints().get(0).getX();
            int y = snake.getPoints().get(0).getY();
            field[x][y] = id;
            for (int i = 1; i < snake.getPoints().size(); i++) {
                int offX = snake.getPoints().get(i).getX();
                int offY = snake.getPoints().get(i).getY();
                if(offX > 0){
                    for (int j = 0; j < offX; j++) {
                        x++;
                        if(x == config.getWidth())
                            x = 0;
                        field[x][y] = id;
                    }
                }
                else if(offX < 0){
                    for (int j = 0; j < -offX; j++) {
                        x--;
                        if(x == -1)
                            x = config.getWidth() - 1;
                        field[x][y] = id;
                    }
                }
                else if(offY > 0){
                    for (int j = 0; j < offY; j++) {
                        y++;
                        if(y == config.getHeight())
                            y = 0;
                        field[x][y] = id;
                    }
                }
                else{
                    for (int j = 0; j < -offY; j++) {
                        y--;
                        if(y == -1)
                            y = config.getHeight() - 1;
                        field[x][y] = id;
                    }
                }
            }
            foods.forEach(food -> field[food.getX()][food.getY()] = -2);
        });
    }

    private synchronized boolean isSquareClean(int x, int y){
        int curX = x, curY = y;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(field[curX][curY] != 0) {
                    return false;
                }
                curY++;
                if (curY == config.getHeight())
                    curY = 0;
            }
            curX += 1;
            if(curX == config.getWidth())
                curX = 0;
        }
        return true;
    }

    public synchronized boolean generateSnake(int id){
        for (int x = 0; x < config.getWidth(); x++) {
            for (int y = 0; y < config.getHeight(); y++) {
                if(isSquareClean(x, y)){
                    if(id > 0) {
                        List<CoordM> points = new ArrayList<>();
                        points.add(new CoordM(2, 2));
                        points.add(new CoordM(-1, 0));
                        SnakeM snake = SnakeM.getBuilder()
                                .setDirection(DirectionM.RIGHT)
                                .setPlayerId(id)
                                .setPoints(points)
                                .setState(SnakeM.StateM.ALIVE)
                                .build();
                        snakes.put(id, snake);
                        x = config.getWidth();
                        y = config.getHeight();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean addPlayer(GamePlayerM player){
        if(generateSnake(player.getId())) {
            players.put(player.getId(), player);
            return true;
        }
        return false;
    }

    private Integer[] generateRandom(int n, int b){
        Set<Integer> generated = new HashSet<>();
        Random r = new Random();
        while (generated.size() < n) {
            generated.add(r.nextInt(b));
        }
        return generated.stream().sorted().toArray(Integer[]::new);
    }

    private synchronized void killSnake(int id, int killer_id){
        if(killer_id != id && players.get(killer_id) != null)
            players.get(killer_id).setScore(players.get(killer_id).getScore() + 10);
        SnakeM snake = snakes.get(id);
        int x = snake.getPoints().get(0).getX();
        int y = snake.getPoints().get(0).getY();
        for (int i = 1; i < snake.getPoints().size(); i++) {
            int offX = snake.getPoints().get(i).getX();
            int offY = snake.getPoints().get(i).getY();
            if(offX > 0){
                for (int j = 0; j < offX; j++) {
                    x++;
                    if(x == config.getWidth())
                        x = 0;
                    if(Math.random() < config.getDeadFoodProb())
                        generateFood(x, y);
                }
            }
            else if(offX < 0){
                for (int j = 0; j < -offX; j++) {
                    x--;
                    if(x == -1)
                        x = config.getWidth() - 1;
                    if(Math.random() < config.getDeadFoodProb())
                        generateFood(x, y);
                }
            }
            else if(offY > 0){
                for (int j = 0; j < offY; j++) {
                    y++;
                    if(y == config.getHeight())
                        y = 0;
                    if(Math.random() < config.getDeadFoodProb())
                        generateFood(x, y);
                }
            }
            else{
                for (int j = 0; j < -offY; j++) {
                    y--;
                    if(y == -1)
                        y = config.getHeight() - 1;
                    if(Math.random() < config.getDeadFoodProb())
                        generateFood(x, y);
                }
            }
        }
        snakes.remove(id);
        players.remove(id);
    }

    public synchronized void setDirection(int id, DirectionM direction){
        nextDirection.put(id, direction);
    }

    private int isIntersectSnake(CoordM coord){ // TODO: 28.11.2021 Обработать пересечение двух голов
        return field[coord.getX()][coord.getY()];
    }

    private boolean isIntersectFood(CoordM coord){
        return field[coord.getX()][coord.getY()] == -2;
    }

    private synchronized void generateFood(int x, int y){
        foods.add(new CoordM(x, y));
        field[x][y] = -2;
    }

    private synchronized void generateFood(int amount){
        int countFreeCages = 0;
        for (int i = 0; i < config.getWidth(); i++) {
            for (int j = 0; j < config.getHeight(); j++) {
                if(field[i][j] == 0)
                    countFreeCages++;
            }
        }
        if(amount > countFreeCages)
            amount = countFreeCages;

        int i = 0, count = 0;
        Integer[] rand = generateRandom(amount, countFreeCages);
        for (int x = 0; x < config.getWidth() && i < amount; x++) {
            for (int y = 0; y < config.getHeight(); y++) {
                if(field[x][y] == 0){
                    if(count == rand[i]) {
                        generateFood(x, y);
                        i++;
                        count++;
                        break;
                    }
                    count++;
                }
            }
        }
    }

    private synchronized void cutSnakeTail(SnakeM snake){
        CoordM coord = snake.getPoints().get(snake.getPoints().size() - 1);
        if(coord.getX() > 0){
            if(coord.getX() == 1)
                snake.getPoints().remove(snake.getPoints().size() - 1);
            else
                snake.getPoints().set(snake.getPoints().size() - 1, new CoordM(coord.getX() - 1, coord.getY()));
        }
        else if(coord.getX() < 0){
            if(coord.getX() == -1)
                snake.getPoints().remove(snake.getPoints().size() - 1);
            else
                snake.getPoints().set(snake.getPoints().size() - 1, new CoordM(coord.getX() + 1, coord.getY()));
        }
        else if(coord.getY() > 0){
            if(coord.getY() == 1)
                snake.getPoints().remove(snake.getPoints().size() - 1);
            else
                snake.getPoints().set(snake.getPoints().size() - 1, new CoordM(coord.getX(), coord.getY() - 1));
        }
        else if(coord.getY() < 0){
            if(coord.getY() == -1)
                snake.getPoints().remove(snake.getPoints().size() - 1);
            else
                snake.getPoints().set(snake.getPoints().size() - 1, new CoordM(coord.getX(), coord.getY() + 1));
        }
    }

    private synchronized void moveSnakeForward(SnakeM snake){
        switch (snake.getDirection()){
            case LEFT -> {
                moveSnakeHead(snake, DirectionM.LEFT);
                CoordM off = snake.getPoints().get(1);
                int newX = off.getX() + 1;
                int newY = off.getY();
                snake.getPoints().set(1, new CoordM(newX, newY));
            }
            case RIGHT -> {
                moveSnakeHead(snake, DirectionM.RIGHT);
                CoordM off = snake.getPoints().get(1);
                int newX = off.getX() - 1;
                int newY = off.getY();
                snake.getPoints().set(1, new CoordM(newX, newY));
            }
            case UP -> {
                moveSnakeHead(snake, DirectionM.UP);
                CoordM off = snake.getPoints().get(1);
                int newX = off.getX();
                int newY = off.getY() + 1;
                snake.getPoints().set(1, new CoordM(newX, newY));
            }
            case DOWN -> {
                moveSnakeHead(snake, DirectionM.DOWN);
                CoordM off = snake.getPoints().get(1);
                int newX = off.getX();
                int newY = off.getY() - 1;
                snake.getPoints().set(1, new CoordM(newX, newY));
            }
        }
    }

    private synchronized void turnSnake(SnakeM snake, DirectionM direction){
        switch (direction){
            case LEFT -> {
                moveSnakeHead(snake, DirectionM.LEFT);
                snake.getPoints().add(1, new CoordM(1, 0));
            }
            case RIGHT -> {
                moveSnakeHead(snake, DirectionM.RIGHT);
                snake.getPoints().add(1, new CoordM(-1, 0));
            }
            case UP -> {
                moveSnakeHead(snake, DirectionM.UP);
                snake.getPoints().add(1, new CoordM(0, 1));
            }
            case DOWN -> {
                moveSnakeHead(snake, DirectionM.DOWN);
                snake.getPoints().add(1, new CoordM(0, -1));
            }
        }
    }

    private synchronized void moveSnakeHead(SnakeM snake, DirectionM direction){
        switch (direction){
            case LEFT -> {
                int newX = snake.getPoints().get(0).getX() - 1;
                if(newX == -1)
                    newX = config.getWidth() - 1;
                int newY = snake.getPoints().get(0).getY();
                snake.getPoints().set(0, new CoordM(newX, newY));
            }
            case RIGHT -> {
                int newX = snake.getPoints().get(0).getX() + 1;
                if(newX == config.getWidth())
                    newX = 0;
                int newY = snake.getPoints().get(0).getY();
                snake.getPoints().set(0, new CoordM(newX, newY));
            }
            case UP -> {
                int newY = snake.getPoints().get(0).getY() - 1;
                if(newY == -1)
                    newY = config.getHeight() - 1;
                int newX = snake.getPoints().get(0).getX();
                snake.getPoints().set(0, new CoordM(newX, newY));
            }
            case DOWN -> {
                int newY = snake.getPoints().get(0).getY() + 1;
                if(newY == config.getHeight())
                    newY = 0;
                int newX = snake.getPoints().get(0).getX();
                snake.getPoints().set(0, new CoordM(newX, newY));
            }
        }
    }

    private synchronized void moveSnake(SnakeM snake, DirectionM direction){
        if(direction.isOpposite(snake.getDirection())){
            moveSnakeForward(snake);
        }
        else if (direction.equals(snake.getDirection())){
            moveSnakeForward(snake);
        }
        else{
            System.out.println(direction);
            turnSnake(snake, direction);
            snake.setDirection(direction);
        }
        int x = snake.getPoints().get(0).getX(), y = snake.getPoints().get(0).getY();
        if(field[x][y] == -2){
            removeFood(x, y);
            if(players.get(snake.getPlayerId()) != null)
                players.get(snake.getPlayerId()).setScore(players.get(snake.getPlayerId()).getScore() + 1);
        }
        else if(field[x][y] > 0){
            snakesToKill.add(new KillEvent(snake.getPlayerId(), field[x][y]));
        }
        else
            cutSnakeTail(snake);
    }

    private synchronized void removeFood(int x, int y){
        int i = 0;
        for (; i < foods.size(); i++) {
            if(foods.get(i).getX() == x && foods.get(i).getY() == y){
                break;
            }
        }
        foods.remove(i);
    }

    public synchronized GameModelMessage nextMove(){
        stateOrder++;
        snakesToKill = new ArrayList<>();
        snakes.forEach((id, snake) ->{
            DirectionM direction = nextDirection.get(id);
            if(direction == null)
                moveSnake(snake, snake.getDirection());
            else
                moveSnake(snake, direction);
        });
        nextDirection.clear();
        updateField();
        snakesToKill.forEach(e -> killSnake(e.deadId, e.killerId));
        if(foods.size() < config.getFoodStatic() + snakes.size() * config.getFoodPerPlayer()){
            generateFood((int)(config.getFoodStatic() + snakes.size() * config.getFoodPerPlayer()) - foods.size());
        }
        return getMessage();
    }
}
