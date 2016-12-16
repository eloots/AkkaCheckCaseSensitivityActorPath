# Akka Actor name case sensitivity

A number of times, the question was asked to me whether Actor names are case sensitive. The answer (luckily I would say) is _yes_.

This small demo app demonstrates this property, including the fact that the case of paths passed to `context.actorSelection` is important.