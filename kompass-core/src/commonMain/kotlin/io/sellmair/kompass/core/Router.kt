package io.sellmair.kompass.core

/**
 * # Router
 * A [Router] controls one region of the screen, that can display distinct routes.
 * E.g.: It is common to have one router for the whole screen. This router might display a `LoginRoute` if
 * the user is not logged in, or a `HomeRoute` once the user registered.
 *
 * ## Example Usage
 * ### Example: Is user logged in?
 * ```
 * if(user.isLoggedIn) {
 *     router.push(HomeRoute())
 * } else {
 *     router.push(LoginRoute())
 * }
 * ```
 *
 *
 * ### Example: Go back by one route
 * ```
 * fun onBackPressed() {
 *     router.pop()
 * }
 * ```
 *
 *
 * ### Example (advanced): Replace current route
 * ```
 * router {
 *     pop().push(NewRoute())
 * }
 * ```
 *
 *
 * ## Note
 * - Instructions sent to a [Router] are not guaranteed to be executed immediately
 * - Instructions sent to a [Router] are not guaranteed to be called just once
 * - Instructions sent to a [Router] will be executed in the order they were dispatched
 * - Instructions sent to a [Router] that is currently not attached will be executed as soon as the router gets attached
 */
interface Router<T : Route> :
    RouterInstructionSyntax<T>,
    PlainStackInstructionSyntax<T, Unit> {

    /**
     * Just syntactic sugar for [RouterInstructionSyntax.instruction]
     * @see RouterInstructionSyntax.instruction
     */
    operator fun invoke(instruction: RouterInstruction<T>): Unit = instruction(instruction)

    override fun plainStackInstruction(instruction: PlainStackInstruction<T>): Unit =
        instruction { this.plainStackInstruction(instruction) }

}


