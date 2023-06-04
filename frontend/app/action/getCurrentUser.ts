import { getServerSession } from "next-auth/next"
import { authOptions } from "@/pages/api/auth/[...nextauth]";

export async function getSession() {
  return await getServerSession(authOptions)
}

export default async function getCurrentUser() {
  try {
    const session = await getSession();

    if (!session?.user?.email) {
      return null;
    }

    const currentUser = session.user;
    if (!currentUser) {
      return null;
    }

    console.log("End")
    return {
      ...currentUser
      // W przypadku GoogleProvider, pola createdAt, updatedAt, emailVerified mogą nie być dostępne
      // Dlatego jeśli są one dla Ciebie ważne, musisz je samodzielnie obsłużyć.
    //   createdAt: currentUser.createdAt?.toISOString() || null,
    //   updatedAt: currentUser.updatedAt?.toISOString() || null,
    //   emailVerified: currentUser.emailVerified?.toISOString() || null,
    };
  } catch (error: any) {
    return null;
  }
}
