![Kompass](https://github.com/sellmair/kompass/blob/develop/assets/Kompass_724.png?raw=true)

## A powerful Kotlin Multiplatform Router for Android and iOS
<br>

![GitHub top language](https://img.shields.io/github/languages/top/sellmair/kompass.svg)
[![Build Status](https://travis-ci.org/sellmair/kompass.svg?branch=develop)](https://travis-ci.org/sellmair/kompass)
![Bintray](https://img.shields.io/bintray/v/sellmair/sellmair/kompass-core.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/sellmair/kompass.svg)
[![Gitter](https://img.shields.io/gitter/room/nwjs/nw.js.svg)](https://gitter.im/kompass-android/)


#### Support
I am happy to help you with any problem on [gitter](https://gitter.im/kompass-android/Help) <br>
Feel free to open any new issue! 

# What Kompass can do for you
- Perfect fit for `MVVM`, `MVI`, `MVP`, `MVX` architectures
- Powerful routing concept that targets multiple platforms like Android, JVM & iOS 
- Easy to use API's
- Highly configurable implementations

## Android
- Flexible routing with fragments
- Built in solution for passing arguments to fragments
- Very easy support for transitions/animations 
- No XML configuration
- Built in `DSL` to configure the `FragmentRouter`
- Survives configuration changes
- Can restore the "routing stack" after process death


# What Kompass *can't* do for now
While the `core` module is currently built and published for multiple platforms (JVM, iOS), there are no
default `Router` implementations for any other platforms than `Android` yet. Those are currently "work in progress". 
`Kompass` can still be used as a common API for routing by providing custom implementations of a `Router` for your platform! 


# Setup

## Step 1: Add the repository
Artifacts are linked to jCenter. Add jCenter repository to your root build.gradle
  
`build.gradle` 
```groovy
  allprojects {
     repositories {
        jcenter()
     }
  }

```

### Step 2: Add the dependency (Multiplatform)

`build.gradle.kts`
```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.sellmair:kompass-core:0.2.0-alpha.5")
            }
        }
        
        /* Optional Android module */
        val androidMain by getting {
            dependencies {
                implementation("io.sellmair:kompass-android:0.2.0-alpha.5")
            }
        }
    }
}
```

### Step 2: Add the dependency (Android Only)

`build.gradle.kts`

```kotlin
dependencies {
    implementation("io.sellmair:kompass-android:0.2.0-alpha.4")
}
```


### Optional Step 3: (Android: Highly encouraged) Enable Kotlin's Android extensions (with `@Parcelize`)

`build.gradle.kts` 

```kotlin
plugins {
    // ...
    id("org.jetbrains.kotlin.android.extensions")
}

// ...

// Currently still necessary for @Parcelize annotation
androidExtensions {
    isExperimental = true
}

```



# Usage
## Example
I recommend having a look at the [example](https://github.com/sellmair/kompass/tree/develop/android-example--fragment) app built with Kompass

<br><br>
###### Gif
<img src="https://github.com/sellmair/kompass/blob/develop/assets/example.gif?raw=true" width="350">
<br><br>

#### Defining routes
Routes can easily be represented by data classes. Let's say your App has three routes that you might want to display:
A `LoginRoute`, `ContactListRoute` and a `ChatRoute`: 

```kotlin

sealed class AppRoute : Route, Parcelable

@Parcelize
class LoginRoute: AppRoute()

@Parcelize
data class ContactListRoute(val contacts: List<Contact>): AppRoute()

@Parcelize
data class ChatRoute(val contact: Contact): AppRoute() 

```

All the arguments necessary to display a certain route should be present in the route itself. 
The example above uses the `@Parcelize` feature from the Kotlin's Android extensions


#### Creating a router instance (Android)

A `FragmentRouter` for Android can be configured quite easily by using the built in DSL for configuration. 

```kotlin
router = FragmentRouter {
            transitions {
                register(LoginToContactListTransition())
                register(ContactListToChatTransition())
            }
            routing {
                route<LoginRoute> { LoginFragment::class }
                route<ContactListRoute> { ContactListFragment::class }
                route<ChatRoute> { ChatFragment::class }
            }
        }
```

The above DSL shows two configurations: 
- `transitions`: configures animations/transitions that should be running when routing
- `routing`: configures which `Fragment` should be displayed for a certain route


#### Setting up a router instance (Android)

A `FragmentRouter` needs a `ViewGroup` to place the fragments in. This can be setup like this:

```kotlin

class MainActivity : AppCompatActivity(), KompassFragmentActivity {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.setup(savedInstanceState, R.id.container)
    }


    override fun onBackPressed() {
        router.popRetainRootImmediateOrFinish()
    }

}

```

Please note: In order to call the `setup` method, one needs to either implement `KomapssFragmentActivity` 
or `KompassFragment`! 



#### Routing (Simple push)

Let's assume that the user taps on a certain contact in the contact list displayed by the `ContactListRoute`: 

```kotlin
class ContactListViewModel {
 
    private val router = TODO("Maybe use DI?")
 
    fun onContactClicked(contact: Contact) {
        router.push(ChatRoute(contact))
    }

}
```

The code above will push the `ChatRoute` onto the "routing stack" and results in the `ChatFragment` being shown. 
Popping the "routing stack" will result in the `ContactListFragment` being displayed again. 


#### Routing (Replacing the current route)

 Let's assume the user successfully logged into your app. This should result in the current `LoginRoute`
being replaced by the `ContactListRoute`


```kotlin
class LoginViewModel {

    private val router = TODO("What about Dagger?")
    
    fun onLoginSuccessful(user: User) {
        router.replaceTopWith(ContactListRoute(user.contacts))
    }
}
```

Wrapping multiple instructions into one lambda block will bundle them to one single operation on the routing stack. 
So you could alternatively write something like 

```kotlin
fun onLoginSuccessful(user: User) {
    router { pop().push(ContactListRoute(user.contacts)) }
}
```


#### Routing (Arbitrary)
Kompass supports `arbitrary` routing: A instruction to the router is nothing more than a function from a list of 
routes to a new list of routes. Let's say your app would like to remove all `ChatRoute` with a certain contact

```kotlin
fun removeContactFromStack(contact: Contact) {
     router {
        with(filter { it.route.contact == contact })
     }
     
     //or
     
     router.plainStackInstruction { filter { it.route.contact == contact } }
}
``` 


#### Receiving the current route inside a `Fragment`

Accessing the route from within the any `Fragment` implementation is easily done by conforming to the `KompassFragment`
interface:

```kotlin

class ContactListFragment : Fragment(), KompassFragment {
   
    override val router: FragmentRouter<AppRoute> = TODO() 
   
    private val route: ContactListRoute by route()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        val contacts = route.contacts 
        
       //...
    }

}
```


#### Fragment Transitions

In order to support animations (fragment transitions) when routing you just need to implement a `FragmentTransition`. 
Example: Your chat app should show a `Slide` transition when going from the `ContactListFragment` to the 
`ChatFragment` and back. Simply implement the transition, check for your constraints and apply 
the transitions to the fragment. It is also possible to apply generic constraints to your transition 
using the `GenericFragmentTransition` API.

```kotlin

class ContactListToChatTransition : FragmentTransition {
    @SuppressLint("RtlHardcoded")
    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment, exitRoute: Route,
        enterFragment: Fragment, enterRoute: Route
    ) {
        if (exitFragment is ContactListFragment && enterFragment is ChatFragment) {
            exitFragment.exitTransition = Slide(Gravity.LEFT)
            enterFragment.enterTransition = Slide(Gravity.RIGHT)
        }

        if (exitFragment is ChatFragment && enterFragment is ContactListFragment) {
            exitFragment.exitTransition = Slide(Gravity.RIGHT)
            enterFragment.enterTransition = Slide(Gravity.LEFT)
        }
    }
}

```

After the transition is implemented, just add it to the configuration of the `FragmentRouter` like seen above!

```kotlin

FragmentRouter { 
    transitions {
        register(LoginToContactListTransition())
    }
}

```