

class LogInController extends AbstractController
{
    UserManager userManager;

    LogInController(UserManager userManager)
    {
        super();
        this.userManager = userManager;
    }

    void connection(String username)
    {
        // TODO: filtrer les caracteres de username > 0
        userManager.setCurrentUsername(username);
        userManager.establishConnection();
    }
}
