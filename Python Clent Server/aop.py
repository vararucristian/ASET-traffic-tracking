import inspect


def aspectize(arg):
    def decorate_method(func):
        func.__before = []
        func.__after = []

        def decorated(*args, **kwargs):
            for f in func.__before:
                res = f(*args, **kwargs)
                if res is not None:
                    return res

            main_res = func(*args, **kwargs)

            for f in func.__after:
                res = f(main_res, *args, **kwargs)
                if res is not None:
                    return res

            return main_res

        decorated.__before = func.__before
        decorated.__after = func.__after

        return decorated

    if inspect.isclass(arg):
        for attr in arg.__dict__:
            if callable(arg.__dict__[attr]):
                setattr(arg, attr, decorate_method(arg.__dict__[attr]))

        return arg

    elif callable(arg):
        return decorate_method(arg)

    else:
        raise Exception("Don't know what to do with argument")


def before(method):
    def decorator(advice):
        method.__before.append(advice)
    return decorator


def after(method):
    def decorator(advice):
        method.__after.append(advice)
    return decorator

