package Screen.ClassRoom;

import Screen.AbstractScreen;

public class ClassRoomMenu extends AbstractScreen {
    private final AddClassRoomScreen addScreen;
    private final SearchClassRoomScreen searchScreen;
    private final DeleteClassRoomScreen deleteScreen;
    private final UpdateClassRoomScreen updateScreen;
    private final ListClassRoomScreen listScreen;

    public ClassRoomMenu() {
        super();
        this.addScreen = new AddClassRoomScreen();
        this.searchScreen = new SearchClassRoomScreen();
        this.deleteScreen = new DeleteClassRoomScreen();
        this.updateScreen = new UpdateClassRoomScreen();
        this.listScreen = new ListClassRoomScreen();
    }

    
}