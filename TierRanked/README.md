# Dev10-Team1-Capstone
## Tentative Plan with Steps
* [X] Project Setup
    * [X] Create a Maven Project. (5 min)
    * [X] Add jUnit 5, Jupiter, as a Maven dependency and refresh Maven. (5 min)
    * [X] Create data, domain, models, controller packages under learn directory
    * [X] Add spring dependencies to maven proejct and refresh
* [X] SQL (Isabel) (1-2 hours)
    * [X] Create database 
    * [X] Create tables with fields and populate with dummy data
    * [X] Create test tables with fields and populate tables with data
    * [X] Run scripts and make test queries
* [X] Backend Data Layer with JDBCTemplateRepositories
    * [X] Categories and AppUser Classes (Kenneth)
        * [X] Categories class with crud (1 hour)
        * [X] Categories test (1 hour)
        * [X] AppUser class with crud(1 hour)
    * [X] Tierlist and Comment Classes (Isabel)
        * [X] Tierlist class with crud (1 hour)
        * [X] Tierlist tests (1 hour)
        * [X] Comment class with crud (1 hour)
        * [X] Comment with tests (1 hour)
    * [X] DisplayProfile Class (Phil)
        * [X] DisplayProfile class with crud (1 hour)
        * [X] DisplayProfile tests (1 hour)
* [X] Backend Domain Layer
    * [X] Response, Result, and ResultType Classes (Kenneth) (0.5 hour)
    * [X] Categories and AppUser Service Classes (Kenneth)
        * [X] Categories class with crud (1 hour)
        * [X] Categories test (1 hour)
        * [X] AppUser class with crud(1 hour)
    * [X] Tierlist and Comment Service Classes (Isabel)
        * [X] Tierlist class with crud (1 hour)
        * [X] Tierlist tests (1 hour)
        * [X] Comment class with crud (1 hour)
        * [X] Comment with tests (1 hour)
    * [X] DisplayProfile Service Class (Phil)
        * [X] DisplayProfile class with crud (1 hour)
        * [X] DisplayProfile tests (1 hour)
* [X] Backend API 
    * [X] Categories and AuthController Controller Classes (Kenneth)
        * [X] Categories controller (1 hour)
        * [X] Manually test Categories http requests (1 hour)
        * [X] AuthController controller (1 hour)
        * [X] Manually test AuthController with http requests (0.5 hours)
    * [X] Tierlist and Comment Controller Classes (Isabel)
        * [X] Tierlist controller (1 hour)
        * [X] Manually test tierlist with http requests (1 hour)
        * [X] Comment controller (1 hour)
        * [X] Test Comment with http requests (1 hour)
    * [X] DisplayProfile Class (Phil)
        * [X] DisplayProfile controller (1 hour)
        * [X] Manually test DisplayProfile with http requests (1 hour)
* [ ] FrontEnd in React with React Bootstrap
    * [ ] TierList form Component (Kenneth) (1-2 hours)
        * [ ] form for creating the tier list
    * [ ] Tierlist Component (Kenneth) (1-2 hours)
    * [ ] Home Component (Isabel) (1-2 hours)
    * [ ] Navbar Component (Isabel) (1-2 hours)
    * [ ] Log In Page (Isabel) (1-2 hours)
    * [ ] Profile Page (Isabel + Kenneth) 
    * [ ] Not Found Page (Isabel) (1 hour)
    * [ ] Contact Us (Isabel) (1 hour)
    * [ ] Category Page (Everbody) (1-2 hours)
        * [ ] will be a drop down that leads to the tierlists 
    * [ ] Security at the end (Everybody) (2 hours)
* [ ] Deploy to AWS (Phil) (7 days)

## Tentative Class Details
### models.AppUser
* private int appUserId
* private String username
* private String password
* private boolean enabled
* private Collection`<GrantedAuthority>` authorities;
* constructor
* Full getters and setters
* Override methods of implemented UserDetails
* private static Collection`<GrantedAuthority>` convertRolesToAuthorities(List`<String>` roles)
### models.TierList
* private int tierListId
* private String name
* private String description
* private LocalDateTime timestamp
* private int s_Tier
* private int a_Tier
* private int b_Tier
* private int c_Tier
* private int d_Tier
* private int f_Tier
* private int upvotes
* private int downvotes
* private int appUserId
* private int categoryId
* Full getters and setters
### models.DisplayProfile
* private int displayProfileId
* private String picture
* private String bio
* private int appUserId
* Full getters and setters
### models.Category
* private int categoryId
* private String name
### models.Photo
* private int photoId
* private String filepath
* private String description
* private int tierListId
### models.Comment
* private int commentId
* private int tierListId
* private String comment
* private LocalDateTime timestamp
* private int appUserId
### data.DataException
* public DataException(String, Throwable)
### data.AppUserJdbcTemplateRepository
* private JdbcTemplate jdbcTemplate
* public AppUser findByUsername (String username)
* public AppUser create(AppUser user)
* public boolean update(AppUser user)
* private void updateRoles(AppUser user)
* private List`<String>` getRolesByUsername(String username)
### data.AppUserRepository
* interface with findByUsername, create, update
### data.AppUserMapper
* implements RowMapper`<AppUser>`
* private final List`<String>` roles;
* public AppUser mapRow(ResultSet rs, int i)
### data.TierListMapper
* implements RowMapper`<TierList>`
* public AppUser mapRow(ResultSet rs, int i)
### data.DisplayProfileMapper
* implements RowMapper`<DisplayProfile>`
* public AppUser mapRow(ResultSet rs, int i)
### data.CategoryMapper
* implements RowMapper`<Category>`
* public AppUser mapRow(ResultSet rs, int i)
### data.PhotoMapper
* implements RowMapper`<Photo>`
* public AppUser mapRow(ResultSet rs, int i)
### data.CommentMapper
* implements RowMapper`<Comment>`
* public AppUser mapRow(ResultSet rs, int i)
### data.TierListJdbcTemplateRepository
* private JdbcTemplate jdbcTemplate
* public List`<TierList>` findAll()
* public List`<TierList>` findByCategory(Category category)
* public List`<TierList>` findByUser(AppUser appUser)
* public TierList add(TierList tierList)
* public boolean update(TierList tierList)
* public boolean deleteById(int tierListId)
### data.TierListRepository
* interface with CRUD methods
### data.DisplayProfileJdbcTemplateRepository
* private JdbcTemplate jdbcTemplate
* public List`<DisplayProfile>` findAll()
* public DisplayProfile findByUser(AppUser appUser)
* public DisplayProfile add(DisplayProfile displayProfile)
* public boolean update(DisplayProfile displayProfile)
* public boolean deleteById(int displayProfileId)
### data.DisplayProfileRepository
* interface with CRUD methods
### data.CategoryJdbcTemplateRepository
* private JdbcTemplate jdbcTemplate
* public List`<Category>` findAll()
* public Category add(Category cateegory)
* public boolean update(Category category)
* public boolean deleteById(int categoryId)
### data.CategoryRepository
* interface with CRUD methods
### data.PhotoJdbcTemplateRepository
* private JdbcTemplate jdbcTemplate
* public List`<Photo>` findAll()
* public Photo add(Photo photo)
* public boolean deleteById(int photoId)
### data.PhotoRepository
* interface with CRUD methods
### data.CommentTemplateRepository
* private JdbcTemplate jdbcTemplate
* public List`<Comment>` findAll()
* public List`<Comment>` findByTierList(TierList tierList)
* public Comment add(Comment comment)
* public boolean deleteById(int commentId)
### data.CommentRepository
* interface with CRUD methods
### domain.TierListService
* private TierListRepository repository
* public TierListService(TierListRepository)
* public List`<TierList>` findAll()
* public List`<TierList>` findByCategory(Category category)
* public List`<TierList>` findByUser(AppUser appUser)
* public Result`<TierList>` add(TierList)
* public Result`<TierList>` update(TierList)
* public Result`<Boolean>` delete(TierList)
* private Result`<TierList>` validate(TierList)
### domain.DisplayProfileService
* private DisplayProfileRepository repository
* public DisplayProfileService(DisplayProfileRepository)
* public List`<DisplayProfile>` findAll()
* public DisplayProfile findByUser(AppUser appUser)
* public Result`<DisplayProfile>` add(DisplayProfile)
* public Result`<DisplayProfile>` update(DisplayProfile)
* public Result`<Boolean>` delete(DisplayProfile)
* private Result`<DisplayProfile>` validate(DisplayProfile)
### domain.CategoryService
* private CategoryRepository repository
* public CategoryService(CategoryRepository)
* public List`<Category>` findAll()
* public Result`<Category>` add(Category)
* public Result`<Category>` update(Category)
* public Result`<Boolean>` delete(Category)
* private Result`<Category>` validate(Category)
### domain.PhotoService
* private PhotoRepository repository
* public PhotoService(PhotoRepository)
* public List`<Photo>` findAll()
* public Result`<Photo>` add(Photo)
* public Result`<Boolean>` delete(Photo)
* private Result`<Photo>` validate(Photo)
### domain.CommentService
* private CommentRepository repository
* public CommentService(CommentRepository)
* public List`<Comment>` findAll()
* public List`<Comment>` findByTierList(TierList tierList)
* public Result`<Comment>` add(Comment)
* public Result`<Boolean>` delete(Comment)
* private Result`<Comment>` validate(Comment)
### domain.Result
* private ArrayList`<String>` messages
* private ResultType type
* private T payload
* public ResultType getType()
* public boolean isSuccess()
* private T getPayload()
* public void setPayload(T payload)
* public List`<String>` getMessages()
* public void addMessage(String message, ResultType type)
### domain.ResultType
* enum with 3 options (SUCCESS, INVALID, NOT_FOUND)
### controllers.TierListController
* private TierListService tierListService
* public List`<TierList>` findAll()
* public List`<TierList>` findByCategory(`@RequestBody` Category category)
* public List`<TierList>` findByUser(`@RequestBody` AppUser appUser)
* public ResponseEntity`<Object>` add(`@RequestBody` TierList tierList)
* public ResponseEntity`<Object>` update(`@PathVariable` int tierListId, `@RequestBody` TierList tierList)
* public ResponseEntity`<Void>` deleteById(`@PathVariable` int tierListId)
### controllers.DisplayProfileController
* private DisplayProfileService displayProfileService
* public List`<DisplayProfile>` findAll()
* public DisplayProfile findByUser(`@RequestBody` AppUser appUser)
* public ResponseEntity`<Object>` add(`@RequestBody` DisplayProfile displayProfile)
* public ResponseEntity`<Object>` update(`@PathVariable` int displayProfileId, `@RequestBody` DisplayProfile displayProfile)
* public ResponseEntity`<Void>` deleteById(`@PathVariable` int displayProfileId)
### controllers.CategoryController
* private CategoryService categoryService
* public List`<Category>` findAll()
* public ResponseEntity`<Object>` add(`@RequestBody` Category category)
* public ResponseEntity`<Object>` update(`@PathVariable` int categoryId, `@RequestBody` Category category)
* public ResponseEntity`<Void>` deleteById(`@PathVariable` int categoryId)
### controllers.PhotoController
* private PhotoService photoService
* public List`<Photo>` findAll()
* public ResponseEntity`<Object>` add(`@RequestBody` Photo photo)
* public ResponseEntity`<Void>` deleteById(`@PathVariable` int photoId)
### controllers.CommentController
* private CommentService commentService
* public List`<Comment>` findAll()
* public List`<Comment>` findByTierList(TierList tierList)
* public ResponseEntity`<Object>` add(`@RequestBody` Comment comment)
* public ResponseEntity`<Void>` deleteById(`@PathVariable` int commentId)
### controllers.ErrorResponse
* private LocalDateTime timestamp
* private String message
* getters, constructor
* public static `<T>` ResponseEntity`<Object>` build(Result`<T>` result)
### controllers.GlobalExceptionHandler
* public ResponseEntity`<ErrorResponse>` handleException(Exceptione e)
* possiblly more specific exceptions
### controllers.AuthController
* private AuthenticationManager authenticationManager
* private JwtConverter converter
* private AppUserService
* public ResponseEntity`<Map<String, String>>` authenticate(`@RequestBody` Map<String, String> credentials)
* public ResponseEntity`<Map<String, String>>` refreshToken(@AuthenticationPrincipal AppUser appUser)
* public ResponseEntity<?> createAccount(`@RequestBody` Map`<String, String>` credentials)
### security.AppUserService
* implements UserDetailsService
* private AppUserRepository repository
* private PasswordEncocer encoder
* public UserDetails loadUserByUsername(String username)
* public Result`<AppUser>` create(String username, String password)
* private Result`<AppUser>` validate(String username, String password) 
* private boolean isValidPassword(String password)
### security.JwtConverter
* private Key key
* private String ISSUER
* private int EXPIRATION_MINUTES
* private int EXPIRATION_MILLIS
* public String getTokenFromUser(AppUser user)
* public AppUser getUserFromToken(String token)
### security.JwtRequestFilter
* extends BasicAuthenticationFilter
* private JwtConverter converter
* protected void doFilerInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
### security.SecurityConfig
* private JwtConverter converter
* public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig)
* public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
### App.java
### AppConfig.java
* public PasswordEncoder getEncoder()
* public WebMvcConfigurer corsConfigurer()

## Tentative Class Diagram

```
src
├───main
│   └───java
│       └───learn
│           └───tier
│               │   App.java         
│               │   AppConfig.java             
│               │
│               ├───data
│               │   └───mappers
│               │            AppUserMapper.java
│               │            TierListMapper.java
│               │            DisplayProfileMapper.java
│               │            CategoryMapper.java
│               │            PhotoMapper.java
│               │            CommentMapper.java
│               │       DataException.java        
│               │       AppUserJdbcTemplateRepository.java
│               │       AppUserRepository.java
│               │       TierListJdbcTemplateRepository.java
│               │       TierListRepository.java      
│               │       DisplayProfileJdbcTemplateRepository.java  
│               │       DisplayProfileRepository.java      
│               │       CategoryJdbcTemplateRepository.java  
│               │       CategoryRepository.java   
│               │       PhotoJdbcTemplateRepository.java  
│               │       PhotoRepository.java
│               │       CommentJdbcTemplateRepository.java  
│               │       CommentRepository.java   
│               │
│               ├───domain
│               │       TierListService.java
│               │       DisplayProfileService.java           
│               │       CategoryService.java
│               │       PhotoService.java
│               │       CommentService.java                         
│               │       Response.java
│               │       Result.java
│               │       ResultType.java    
│               │
│               ├───models
│               │       AppUser.java        
│               │       TierList.java
│               │       DisplayProfile.java
│               │       Category.java
│               │       Photo.java
│               │       Comment.java       
│               │
│               ├───controllers
│                      TierListController.java
│                      DisplayProfileController.java
│                      CategoryController.java
│                      PhotoController.java
│                      CommentController.java
│                      AuthController.java
│                      ErrorResponse.java
│                      GlobalExceptionHandler.java
│
└───test
    └───java
        └───learn
            └───tier
                ├───data  
                │
                └───domain
```

