package io.sellmair.kompass.app

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

abstract class TargetView : LinearLayout, TargetIdentifiable {

    constructor(context: Context, destination: Destination) : this(context)

    constructor(context: Context?) : super(context)

    protected fun init(context: Context, destination: Destination) {
        View.inflate(context, R.layout.fragment_target, this)
        val targetId = findViewById<TextView>(R.id.target_id)
        val targetKey = findViewById<TextView>(R.id.target_key)

        setBackgroundColor(id.color)
        targetId.text = id.name
        targetKey.text = destination.key
    }

}


class ViewOne : TargetView {

    override val id: Target = Target.ViewOne

    constructor(context: Context, destination: Destination) : super(context, destination) {
        init(context, destination)
    }

    constructor(context: Context?) : super(context)

}

class ViewTwo : TargetView {

    override val id: Target = Target.ViewTwo

    constructor(context: Context, destination: Destination) : super(context, destination) {
        init(context, destination)
    }

    constructor(context: Context?) : super(context)

}


class ViewThree : TargetView {

    override val id: Target = Target.ViewThree

    constructor(context: Context, destination: Destination) : super(context, destination) {
        init(context, destination)
    }

    constructor(context: Context?) : super(context)

}


class ViewFour : TargetView {

    override val id: Target = Target.ViewFour

    constructor(context: Context, destination: Destination) : super(context, destination) {
        init(context, destination)
    }

    constructor(context: Context?) : super(context)

}


class ViewFive : TargetView {

    override val id: Target = Target.ViewFive

    constructor(context: Context, destination: Destination) : super(context, destination) {
        init(context, destination)
    }

    constructor(context: Context?) : super(context)

}

