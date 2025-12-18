import type { ReactNode } from 'react';
import Navbar from './Navbar';
import Footer from './Footer';

// updated for Stripe-safe banner
interface LayoutProps {
  children: ReactNode;
  showNavbar?: boolean;
  showFooter?: boolean;
}

export default function Layout({
  children,
  showNavbar = true,
  showFooter = true
}: LayoutProps) {
  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-gray-50 to-gray-100">
      {/* updated for Stripe-safe banner - Internal Prototype Notice */}
      <div className="bg-amber-100 border-b border-amber-200 px-4 py-2">
        <p className="text-center text-amber-800 text-sm font-medium">
          This site is an internal prototype for testing Stripe Issuing and spend-control workflows.
          It is not a public financial product and is accessible only to invited test users.
        </p>
      </div>

      {showNavbar && <Navbar />}

      <main className="flex-1">
        {children}
      </main>

      {showFooter && <Footer />}
    </div>
  );
}
