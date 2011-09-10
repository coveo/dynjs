package org.dynjs.runtime.linker;

import org.dynalang.dynalink.linker.CallSiteDescriptor;
import org.dynalang.dynalink.linker.GuardedInvocation;
import org.dynalang.dynalink.linker.GuardingDynamicLinker;
import org.dynalang.dynalink.linker.LinkRequest;
import org.dynalang.dynalink.linker.LinkerServices;
import org.dynalang.dynalink.support.Guards;
import org.dynjs.runtime.DynAtom;
import org.dynjs.runtime.RT;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class DynJSLinker implements GuardingDynamicLinker {
    @Override
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        CallSiteDescriptor callSiteDescriptor = linkRequest.getCallSiteDescriptor();
        if ("print".equals(callSiteDescriptor.getName())) {
            MethodType methodType = callSiteDescriptor.getMethodType();
            MethodHandle print = lookup().findStatic(RT.class, "print", methodType);

            return new GuardedInvocation(print, null);
        }
        return null;
    }
}
