'use client';
import { use, useEffect, useState } from "react";

interface ClientOnlyProps{
    children: React.ReactNode;
}

const ClientOnly: React.FC<ClientOnlyProps> = ({
    children
}) => {
    const [hasMounted, setHesMounted] = useState(false);

    useEffect(()=> {
            setHesMounted(true);
        }, []);
    
    if(!hasMounted){
        return null;
    }
    return (
        <>
        {children}
        </>
     );
}

export default ClientOnly;