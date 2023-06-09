import NextAuth, {AuthOptions} from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"
import GoogleProvider from "next-auth/providers/google"

export const authOptions: AuthOptions = {
    providers: [
        GoogleProvider({
            clientId: "794839251456-c6sna97nt0qh1i31l3f2h9ksj9j5s6eo.apps.googleusercontent.com" as string,
            clientSecret: "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" as string
        })
    ],
    pages: {
        signIn: '/',
    },
    debug: process.env.NODE_ENV === 'development',
    session: {
        strategy: "jwt",
    },
    secret: process.env.NEXTAUTH_SECRET,
    callbacks: {
        async jwt({token, account}) {
            if (account) {
                token.id_token = account.id_token;
                token.accessToken = account.access_token;
            }
            return token;
        },
        async session({session, token}) {
            session.id_token = token.id_token;
            return session;
        },
    },
}

export default NextAuth(authOptions);