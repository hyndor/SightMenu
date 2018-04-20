package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.junit.*;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;
import ru.hyndo.sightmenu.util.MenuApiUtil;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SessionResolverImplTest {

    MenuTemplate mainTemplate;
    Player vasya = mock(Player.class);
    Player petya = mock(Player.class);
    MenuApiInstance apiInstance = MenuApiUtil.createApiInstance();
    SessionResolverImpl sessionResolver = (SessionResolverImpl) apiInstance.getSessionResolver();
    MenuTemplate randomTemplate = MenuApiUtil.createRandomTemplate(apiInstance);
    MenuTemplate veryRandomTemplate = MenuApiUtil.createRandomTemplate(apiInstance);

    @Before
    public void setUp() throws Exception {
        MenuApiUtil.setupBukkit();
    }

    @Test
    public void findAllByTemplate() {
        MenuSession vasyaSes = apiInstance.getMenuFactory().createSingleSession(vasya, randomTemplate);
        sessionResolver.addNewSession(vasyaSes);
        MenuSession petyaSes = apiInstance.getMenuFactory().createSingleSession(petya, randomTemplate);
        sessionResolver.addNewSession(petyaSes);
        Collection<MenuSession> allByTemplate = sessionResolver.findAllByTemplate(randomTemplate);
        assertEquals(allByTemplate.size(), 2);
        assertTrue(allByTemplate.contains(vasyaSes));
        assertTrue(allByTemplate.contains(petyaSes));
    }


    @Test
    public void getLastPaginatedSession() {
        PaginatedMenuTemplate paginatedMenuTemplate = apiInstance.templateBuilder().paginatedTemplate()
                .setMainPage(randomTemplate)
                .withPage(randomTemplate)
                .withPage(veryRandomTemplate)
                .build();
        PaginatedMenuSession paginatedSession = apiInstance.getMenuFactory().createPaginatedSession(vasya, paginatedMenuTemplate);
        Optional<PaginatedMenuSession> lastPaginatedSession = sessionResolver.getLastPaginatedSession(vasya);
        assertTrue(lastPaginatedSession.isPresent());
        assertEquals(lastPaginatedSession.get(), paginatedSession);
    }

    @Test
    public void getSession() {
        MenuSession vasyaSes = apiInstance.getMenuFactory().createSingleSession(vasya, randomTemplate);
        sessionResolver.addNewSession(vasyaSes);
        MenuSession petyaSes = apiInstance.getMenuFactory().createSingleSession(petya, randomTemplate);
        sessionResolver.addNewSession(petyaSes);
        Optional<MenuSession> session = sessionResolver.getSession(vasya);
        assertTrue(session.isPresent());
        assertEquals(session.get(), vasyaSes);
        Optional<MenuSession> session1 = sessionResolver.getSession(petya);
        assertTrue(session1.isPresent());
        assertEquals(session1.get(), petyaSes);
    }
}